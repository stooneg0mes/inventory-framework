package me.saiintbrisson.minecraft;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class AbstractViewSlotContext extends BaseViewContext implements ViewSlotContext {

	@Getter(AccessLevel.NONE)
	private final BaseViewContext parent;

	@Getter(AccessLevel.PACKAGE)
	private final ViewItem backingItem;

	@ToString.Include
	private boolean cancelled;

	@Setter(AccessLevel.NONE)
	private Object item;

	private boolean changed;

	AbstractViewSlotContext(ViewItem backingItem, @NotNull final BaseViewContext parent) {
		super(parent.getRoot(), parent.getContainer());
		this.backingItem = backingItem;
		this.parent = parent;
	}

	@Override
	public final @NotNull ViewContextAttributes getAttributes() {
		return parent.getAttributes();
	}

	@Override
	protected final ViewItem[] getItems() {
		return parent.getItems();
	}

	@Override
	public final Object getItem() {
		return item;
	}

	@Override
	public final void setItem(@Nullable final Object item) {
		inventoryModificationTriggered();
		this.item = PlatformUtils.getFactory().createItem(item);
		setChanged(true);
	}

	@Override
	public final boolean hasChanged() {
		return changed;
	}

	public final void setChanged(final boolean changed) {
		this.changed = changed;
	}

	@Override
	public void updateSlot() {
		getRoot().update(this, getSlot());
	}

}
